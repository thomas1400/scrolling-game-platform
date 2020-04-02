package plan;


import javafx.scene.image.ImageView;

/**
 *  This interface governs Manageable objects (particularly Entities) and how they can be managed
 *  by Manager Objects (particularly our Entity Manager and Renderer).
 */
public abstract class Entity extends ImageView implements Manageable{
    /**
     * Start of the program.
     */
    public void updateVisualization() {
        
    }
}
