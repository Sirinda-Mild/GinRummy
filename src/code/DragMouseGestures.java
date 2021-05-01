/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DragMouseGestures {

        double orgSceneX, orgSceneY;
        double orgTranslateX, orgTranslateY;

        public void makeDraggable(Node node) {
            
        }

        

//        EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent t) {
//
//                double offsetX = t.getSceneX() - orgSceneX;
//                double offsetY = t.getSceneY() - orgSceneY;
//
//                double newTranslateX = orgTranslateX + offsetX;
//                double newTranslateY = orgTranslateY + offsetY;
//
//                Node p = ((Node) (t.getSource()));
//
//                p.setLayoutX(newTranslateX);
//                p.setLayoutY(newTranslateY);
//
//                for (Node c : Node) {
//
//                    if (c == p) {
//                        continue;
//                    }
//
//                    if (c.getBoundsInParent().intersects(p.getBoundsInParent())) {
//                        System.out.println("Overlapping!");
//                    }
//                }
//            }
//        };
    }

