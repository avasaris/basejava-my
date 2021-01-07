package ru.basejava.resume.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.stream.Stream;

@XmlAccessorType(XmlAccessType.FIELD)
abstract public class Section<T> implements Serializable {

    public abstract int size();

    public abstract Stream<T> getItemsStream();

    public abstract void addItem(T readUTF);
}
