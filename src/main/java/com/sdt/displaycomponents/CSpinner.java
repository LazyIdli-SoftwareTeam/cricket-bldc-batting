/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;
/**
 *
 * @author srikanth.possim
 */
public  class  CSpinner<T> extends Spinner<T>{
    int min = Integer.MIN_VALUE;
    int max = Integer.MAX_VALUE;
    
    public CSpinner() {
        super();
        addListenerKeyChange();
        setEditable(true);
    }

    public CSpinner(int min, int max, int initialValue) {
        super(min, max, initialValue);
        this.min = min;
        this.max = max;
        addListenerKeyChange();
        setEditable(true);
    }

    public CSpinner(int min, int max, int initialValue, int amountToStepBy) {
        super(min, max, initialValue, amountToStepBy);
        this.min = min;
        this.max = max;
        addListenerKeyChange();
        setEditable(true);
    }

    public CSpinner(double min, double max, double initialValue) {
        super(min, max, initialValue);
        this.min = (int)min;
        this.max = (int)max+1;
        addListenerKeyChange();
        setEditable(true);
    }

    public CSpinner(double min, double max, double initialValue, double amountToStepBy) {
        super(min, max, initialValue, amountToStepBy);
        this.min = (int)min;
        this.max = (int)max+1;
        addListenerKeyChange();
        setEditable(true);
    }

    public CSpinner(ObservableList<T> items) {
        super(items);
        addListenerKeyChange();
        setEditable(true);
    }

    public CSpinner(SpinnerValueFactory<T> valueFactory) {
        super(valueFactory);
        addListenerKeyChange();
        setEditable(true);
    }
    
    
    
    private void addListenerKeyChange() {
        getEditor().textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) { 
                double tempvalue = min;
                try {
                    tempvalue =Double.parseDouble(getEditor().getText());
                } catch (Exception e) {
                }
                if(tempvalue>=min)
                    commitEditorText();
            }
        });
    }

    private void commitEditorText() {
        if (!isEditable()) return;
        String text = getEditor().getText();
        SpinnerValueFactory<T> valueFactory = getValueFactory();
        if (valueFactory != null) {
            StringConverter<T> converter = valueFactory.getConverter();
            if (converter != null) {
                try {
                    T value = converter.fromString(text);
                    valueFactory.setValue(value);
                } catch (Exception e) {
                }                
            }
        }
    }

}
