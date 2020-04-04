package ooga.model.entity;


/**
 *  This interface governs Manageable objects (particularly Entities) and how they can be managed
 *  by Manager Objects (particularly our Entity Manager and Renderer).
 */
public interface Renderable {
    /**
     * Start of the program.
     */
    public void updateVisualization();
}
