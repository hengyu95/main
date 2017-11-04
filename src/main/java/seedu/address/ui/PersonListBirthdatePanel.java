package seedu.address.ui;

import java.util.Set;
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author hengyu95
/**
 * Panel containing the list of persons.
 */
public class PersonListBirthdatePanel extends UiPart<Region> {
    private static final String FXML = "PersonListBirthdatePanel.fxml";
    private static final int SCROLL_INCREMENT = 11;
    private final Logger logger = LogsCenter.getLogger(PersonListBirthdatePanel.class);

    @FXML
    private ListView<PersonCard> personListView2;

    @FXML
    private ScrollBar personListViewScrollBar;

    public PersonListBirthdatePanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);

        setPersonListViewScrollBar();
        setConnections(personList);
        registerAsAnEventHandler(this);
    }



    /**
     * Scrolls one page down
     */
    public void scrollDown() {
        Platform.runLater(() -> {
            if (personListViewScrollBar == null) {
                setPersonListViewScrollBar();
            }

            for (int i = 0; i < SCROLL_INCREMENT; i++) {
                personListViewScrollBar.increment();
            }
        });
    }

    /**
     * Scrolls one page up
     */
    public void scrollUp() {
        Platform.runLater(() -> {
            if (personListViewScrollBar == null) {
                setPersonListViewScrollBar();
            }

            for (int i = 0; i < SCROLL_INCREMENT; i++) {
                personListViewScrollBar.decrement();
            }
        });
    }

    /**
     * Initializes personListViewScrollBar and assigns personListView's scrollbar to it
     */
    private void setPersonListViewScrollBar() {
        Set<Node> set = personListView2.lookupAll(".scroll-bar");
        for (Node node: set) {
            ScrollBar bar = (ScrollBar) node;
            if (bar.getOrientation() == Orientation.VERTICAL) {
                personListViewScrollBar = bar;
            }
        }
    }

    private void setConnections(ObservableList<ReadOnlyPerson> personList) {
        ObservableList<PersonCard> mappedList = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1));
        personListView2.setItems(mappedList);
        personListView2.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListView2.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            personListView2.scrollTo(index);
            personListView2.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }
}