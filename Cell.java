package xzero.model;

import xzero.model.events.CellActionEvent;
import xzero.model.events.CellActionListener;

import java.awt.Point;
import java.util.ArrayList;

/*
 * Ячейка, являющаяся составной частью поля и содержащая в себе метку
 */
public class Cell{
    // --------------------- Позиция метки -----------------------

    // --------------------------------------------------------------
    private Point _position;
    
    void setPosition(Point pos){
        _position = pos;
    }

    public Point position(){
        return (Point)_position.clone();
    }
    
// --------- Метка, принадлежит полю. Принадлежность задает само поле ------
    private GameField _field;
    
    void setField(GameField f){
        this._field = f;
    }

    void removeField() {this._field = null;}
// --------------------- Метка, принадлежащая ячейке -----------------------
   // TODO реализация связи с классов Label (агрегация в сторону Cell)
    private Label _label;

    public void setLabel(Label l) {
        if(isEmpty()) {
            this._label = l;
            _label.setCell(this);
        }
    } // TODO

    //TODO Игрок однократно может удалить любую метку с поля. После удаление метки игрок сохраняет свой ход. -----------МОДИФИКАЦИЯ
    public void removeLabel(Label l) {
        /*this._label.setCell(this);
        this._label = null;*/
        if(!isEmpty()) {
            l.setCell(this);
            this._label = null;
            System.out.println("\tCELL: Удаление метки.");
        }
    }

    public Label label() {
        return this._label;
    }
    
    public boolean isEmpty(){
        return this._label == null;
    }

    // TODO--- Сделать список слушателей
    private ArrayList<CellActionListener> _cellListenerList = new ArrayList();

    // Присоединяет слушателя, который следит за меткой
    public void addCellActionListener(CellActionListener l) {
        this._cellListenerList.add(l);
    }

    public void messageDeletingLabel() {
        CellActionEvent event = new CellActionEvent(this);
        event.setCell(this);
        event.setPoint(position());

        for (CellActionListener listener : _cellListenerList) {
            listener.removeLabelonCell(event);
        }
    }
}
