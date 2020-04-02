package plan;


/**
 *  This interface governs Manageable objects (particularly Entities) and how they can be managed
 *  by Manager Objects (particularly our Entity Manager and Renderer).
 */
public interface Displayable {
    /**
     * Start of the program.
     */
    public void updateVisualization();
}
