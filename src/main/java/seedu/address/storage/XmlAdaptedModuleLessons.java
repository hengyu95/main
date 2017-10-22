package seedu.address.storage;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;

import java.util.ArrayList;
import java.util.Map;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedModuleLessons {

    @XmlAttribute
    private String lessonType;
    @XmlValue
    private String lessonSlot;

    /**
     * Constructs an XmlAdaptedModuleLessons.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedModuleLessons() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedModuleLessons(Map.Entry<String, String> source) {
        lessonType = source.getKey();
        lessonSlot = source.getValue();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public ArrayList<String> toNusLesson() throws IllegalValueException {
        ArrayList<String> nusLesson = new ArrayList<>();
        nusLesson.add(lessonType);
        nusLesson.add(lessonSlot);
        return nusLesson;
    }

}
//TODO: CHANGE COMMENTS AND FIX CHECKSTYLE