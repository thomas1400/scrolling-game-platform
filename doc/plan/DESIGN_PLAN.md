# DESIGN_PLAN.md


## Introduction

In this project, we intend to create a scrolling game (similar to Mario), where a player moves to progress through a level defeating enemies and gaining power ups/penalties as they go. The goal of this project is to make our code as flexible as possible, so that extensions and new features can be added very easily. For example, a few of the flexible features that we want our project to have is to be able to easily add new levels, new characters, new enemies, new features that any character can have, and different interactions between characters. At a high level, our project is broken down in a model (back-end), control, and view (front-end). 

The front-end will contain information regarding the set of different display screens, how the animation is displayed, and how the screen moves based upon the movements of a the user's character. The controller will be responsible for passing information between the model and the view, such as the number of lives the character has or if the character walks off of a cliff. 

The back-end will be responsible for the keeping track of the features for each of back-end objects that having corresponding front images. For example, the back-end will store information regarding if an object has the ability to shoot fire-balls or how high the character can jump. The back-end will also contain information regarding how to handle collisions between objects. 

The design will be closed in that the front end will not be able to access or changes objects in the back-end. The design will be open so that there can be communication regarding information like how many lives the character has if a level has been won. 

## Overview

![](https://i.imgur.com/enflXpf.jpg)

We plan to divide the project into a model (orange circle), view (green circle), overall game controller (blue circle), and level specific game engine (red circle). The model will manage all of the properties of each 'entity' (such as an enemy or player), 

### Model:
The model section will serve the purpose of managing game rules and physics governing the movement of objects. There will be five main types of objects that interact with each other: 
* `Player`
    * Only user 'moveble' object
        * We will problem actually be moving everything BUT this (as in, move the screen and everything on the screen while the character *appears* to be moving), but this will be the only object the user can interact with.
* `Enemy`
    * Different enemy types that interact with the player in different ways
    * can be stunned
* `Item`
    * Power up and penalty subclasses
* `Projectile`
    * Similar to enemy, except it cannot be stunned
* `Terrain`
    * Platforms that players can stand on, walls, etc
* `Physics` objects
    * Govern the physics of the game
    * `Entity`'s characteristics change using the game specific physics


### View:
* Creates and displays the game's different screens
    * User Select
    * Main Menu
    * Level Select
    * Game Screen
    * Death Screen
    * Statistics Screen
    * Help Screen
* Input from the menus is communicated via the Screen Controller to the backend.

### Game Controller:
* Launch the game, and connect the game engine to the view. This will keep track of how many lives the player has, the current level, and manage saving current games

    #### High-Level Application Control
    * Organizes, creates, and saves `User` objects to be used in various screens and the game itself.
    * Acts as the Main Application window, controlling which screen is currently visible based on the user's interactions with the application
    #### Mid-Level Game Control
    * Parses level files and creates new `Level` objects complete with all entities based off of information present in the specific `.level` file.
    * Passes Background Image and created `ImageView Entities` to Game Screen
    * Passes Initial `Level`, complete with `User` and initial `EntitiesList` objects into the Level Loop
    * Initializes and controlls starting and stopping of the `LevelLoop`

### Game Engine/Level Controller:

Game Engine (Level Loop):
* Manages user input
* Checks for collisions
    * Creates Collision objects based on hit box detection
    * Passes Collisions to the Entites they correspond to
* Updates all Entities
* Adjusts Camera/Viewport
    * Determines which entities should be visible, and which should not. Changes their tags so they can be visualized accordingly by the front end
* Adds or removes appropriate Entities from the LevelController
* Waits for the remainder of its 60th of a second (to maintain a constant 60fps game tick)
* Begins Loop Again

## Design Details

### View:
The View module is fairly simple: it contains the Screens to be displayed by the Application. Each Screen extends the `Screen` class, which implements the `Displayable` interface. All Displayable objects contain a method `getDisplay` that returns a JavaFX Group.

Each of the Screens contains several buttons, like a "Back" or "Start Game" button. The behavior of these buttons is pulled from properties files, and the result is sent back to the ScreenController, which manages navigation between Screens.

The View contains 7 unique views, for Basic, and potentially an 8th, the Level Builder, in Complete:
* User Select
    * Select a user profile, which holds information related to level progress, lives, and score.
* Main Menu
    * Navigate to the level select screen, the statistics screen, the help screen, and potentially, the Level Builder screen. Show an image of the game and the title.
* Level Select
    * Select a level, and press "Begin" to start the game. Potentially, click on items to start the level with a power-up. This screen has a button to go back to the main menu.
* Game Screen
    * This screen receives game rendering data communicated through the GameController to the ScreenController in order to display the game. It has buttons to open the settings menu and quit, along with the ability to display remaining lives and score in a toolbar.
* Splash Screen
    * This splash screen shows upon loading a level, exiting a level, and death. It displays the user's remaining lives, and the level being loaded/exited.
* Statistics Screen
    * This screen shows miscellaneous statistics, decided by the programmers, to inform the user of their progress in the game, with a button to go back to the main menu.
* Help Screen
    * This screen shows keybindings and helpful tips for the new user, with a button to go back to the main menu.


### Game Controller:
The Game Controller Module of this project is responsible for two seperate sub-modules, the Main Ooga Controll, and the Central Level Controller. They are detailed as follows:

#### High-Level Application Control
* Acts as the Main Application window, controlling which screen is currently visible based on the user's interactions with the application
    * The `Main` class is simply responsible for beginning the application by creating a new `Ooga` instance.
    * The `Ooga` class acts as a whole application controller, and is responsible for the organization of Application `Users` and the `ScreenController`. 
        * It will be able to read in these users from saved `.user` data files and also save them if prompted. It will create `User` classes from each one of these files which will have methods such as `updateLives()` and `getLevelUnlocked()` to govern its interaction with the rest of the game.
        * It will be able to create and handle the `Screen Controller` instance as well
        * Organizes, creates, and saves `User` objects to be used in various screens and the game `Levels` themselves.
    * The `ScreenController` class first and foremost contains the main Window that the user is able to interact with. Its main job is to control which of the 5+ sub windows is being displayed at any given time.
        * used methods such as `switchToLevel(Level level)` to make changes to its displayed level.
        * Has access to `User` so that it can display the correct images on the initial screens, and pass user parameters back to the game.


#### Mid-Level Game Control
* Parses level files and creates new `Level` objects complete with all entities based off of information present in the specific `.level` file.
    * The `LevelController` class is responsible for governing the actions between the Level Loop and the Game Screen classes. The following classes are helper classes that allow it to do this job of communication and translation.
    * The `Level` class is responsible for containing the `EntityList` and `User` information which is pertinent to the back-end game engine.
    * The `LevelParser` class is responsible for creating entities with the correct properties based off of the `.level` files that it reads in. This will involve a realtively intense error checker that will for instance, determines whether a user is attempted to be created twice.
        * Passes Background Image and created `ImageView Entities` to Game Screen
        * Passes Initial `Level`, complete with `User` and initial `EntitiesList` objects into the Level Loop
        * Initializes and controlls starting and stopping of the `LevelLoop`
    * The `Communicable` Interface allows for the Level loop to have a limited set of methods available for use, mainly the `addEntity()` and `removeEntity()` methods which allow for the Level Controller to manage adding and removing entities from the groups/panes that exist in the front end game screen.


### Game Engine:
* Game Engine (Level Loop): The Purpose of the Game Engine is to launch a level, creating a LevelLoop class that manages the communication between the model and the view. The Game Engine will be responsible for handling user input from the keyboard, handling collisions, adding and removing entities that result from a change in the camera view or the death or creation of a character, and communicating with the specific entities and the level controller. The main class of this module is the LevelLoop class. The LevelLoop will create a Timeline that will constatly checks for UserInput, Collisions, updates the EntityList, and updates the position of the Camera. This class will contain the following instance variables:
    * `private EntityList myEntitiesList`
    * `private EntityManager myEntityManager`
    * `private CameraManager myCameraManager`
    * `private CollisionManger myCollisionManager`
    * `private InputManager myInputManager`
    * `private Level myLevel`
    * `private User myUser`
    
    These instance variables will be instances of the other classes which will be described below. This class will be the centralized location in which each of these "manager" classes will report to. The LevelLoop class will have the following methods.
    * `private void manageInput()`
        * The purpose of this method is to handle the input the user enters via the keyboard- such as telling the player to move left or right or to jump. This method will work directly with the InputManager.
    * `private void manageCollisions()`
        * The purpose of this method is to make any changes to the entitiess list based upon collisions that occur between objects
    * `private void updateEntities()`
        * The purpose of this method is to to tell the LevelController to add or remove specific Nodes (Entities) from the front-end
    * `private void updateCamera()`
        * The purpose of this method is tell the view to recenter around the position of Mario (or the main character) and to update the repective positions of the other entities in the game 

    The LevelLoop class will also implement the Loopable interface. As a result, the LevelLoop will have the following public methods:
    * `public void begin()`
    * `public void pause()`
    * `public void resume()`
    * `public void exit()`

    These methods have the ability to be called by the LevelController to start, pause, resume, or exit a level.

    The EntityList class will serve the purpose of a custom data structure that are creating to store Entities. The EntityList class will have the following instance variable:
    * `private Entity[] entities`
        * This variable will store all of the entities that have been created (and have not yet been destroyed)
        
    The EntityList class will have the following methods:
    * `protected void addEntity()`
        * The purpose of this method is to add entities to the entity list. 
    * `protected void removeEntity()`
        * The purpose of this method is to remove entities from the entity list. 
        
    This is a protected method because it has to be able to be called by the LevelLoop, but should only be able to be called within the GameController module (package). This class will be used by the LevelLoop class to control the entities that will be passed to the LevelController. 
    
    The CameraManager class will serve the purpose of managing the entities that are present on the current displaying as the player moves through the game. This includes update the relative coordinates of each entity. The CameraManager class will have the following methods:
    * `protected void manageCamera(EntityList entities)`
        * The purpose of this method is to alter the boolean of each entity that contains the information regarding if the entity is currently displayed on the screen. 
    * `private void updateCoordinates()`
        * This method will be called by manageCamerea to update the relative coordinates of the entities present on the screen. 

    The EntityManager class will have the purpose of taking the changes from the other manager classes and adding and removing the respective entities. This class will have the following method:
    * `protected void manageEntities(EntityList entities)`
        * The purpose of this method will be add and remove necessary entities

    The EntityManager class will implement will implement the Communicable interface. Therefore, it will have the following public methods:
    * `void addEntity()`
        * The purpose of this class is to add Entities to the EntityList
    * `void removeEntity()`
        * The purpose of this class is to remove Entities from the EntityList

    The InputManager class will serve the purpose of handling the input from the use. The InputManager class will have the following method:
    * `protected void handleKeyInput()`
        * This method will recognize the input from the user and call the respective method to respond to the input
    * `private void handleLeftKey()`
    * `private void handleRightKey()`
    * `private void handleUpKey()`
    * `private void handleDownKey()`
    * `private void handleSpaceKey()`
    
    The CollisionManager class will serve the purpose of detecting collisions between entities. The CollisionManager will have the following methods:
    * `protected void manageCollisions()`
        * The purpose of this method is to check for collisions between the entities. 
    * `private Collision sendCollision()`
        * The purpose of this method is call a method within a specific Entity that will pass a Collision object to the entity. This method will be called when a collision is detected
     
    The Collision class will create an object that stores information regarding the type of Collision and what the Entity collided with. The Collision class will have the following instance variables:
    * `private Entity entityCollidedWith`
    * `private String collisionType`

The functionality of the Collision is described below. 

#### Collision Behavior Model:

The collision behavior of each Entity class is stored in a file called "\<EntityClassName>.colbehavior", which is loaded upon instantation as a CollisionBehaviorBundle.

Whenever two Entities collide, a CollisionEvent instance is created, which contains information about the two Entities that collided and on which side the collision happened. That CollisionEvent is passed to each of the involved Entities, which process the CollisionEvent and conduct behavior according to their CollisionBehaviorBundle in the following way:

Each Entity unpacks the CollisionEvent to find the type of the other Entity involved. Then, the Entity searches its CollisionBehaviorBundle for a behavior that matches the other Entity's type. It does so by first checking if the other Entity has a "Collision Behavior Override" tag -- a tag stored as an instance variable which indicates an override of typical collision behavior, such as when a Player becomes 'invulnerable' or a Koopa is 'stunned'. The Entity searches its CollisionBehaviorBundle for a behavior that matches this override tag. If no behavior is found, the Entity moves on, and searches its CollisionBehaviorBundle for a behavior that matches the other Entity's class name. If this behavior is not found, the Entity moves on, and searches its CollisionBehaviorBundle for a behavior that matches the other Entity's superclass' name, and so on, moving up the hierarchy of inheritance shown below.

![](https://i.imgur.com/enflXpf.jpg)


For example, if a Player and a Koopa marked as 'StunnedKoopa' collide, the Player searches for a behavior for a 'StunnedKoopa'. If no behavior is found, it searches for a behavior for a Koopa. If that behavior is not found, it searches for a behavior for an Enemy, and if that isn't found, it searches for the behavior for an Character.

After this entire process, if the Entity can't find the correct behavior, it finds the behavior file for its superclass, and repeats the process described above, searching for the same behaviors in its superclass' CollisionBehaviorBundle.

For example, if a Koopa and a Fireball collide, the Koopa searches for the behavior for a Fireball. If no behavior is found, it searches for a behavior for a Projectile. If that isn't found either, then the Koopa begins to search in its superclass's (Enemy's) behavior file. It searches the Enemy behavior file for the behavior for a Fireball. If that behavior isn't found, it searches the Enemy behavior file for a behavior for a Projectile. Finding that an Enemy dies upon contact with a Projectile, the Koopa calls the methods needed to die.

### Model
The Purpose of Model is to hold all of the different attributes an entity can have, and contrust entites based on those attributes. These entities will respond to collision events, and interact with the 'environment' based on rules in the physics engine.

#### Entity Sub-Module
The main class within the model will be `Entity`, and it will extend `ImageView`. This class will have a list of attributes specified in the XML File, and will create it's attributes based on strings of attributes passed in. It will use reflection to create the specific attributes. These attributes will be whether or not the entity can move, if the user can control them, the health it has, how it responses to attacks from the side, above, below, and whether or not it is stunnable. There may be more attributes as we expand the complexity what the game allows!

This class extends `Imageview` because each entity will have an associated image and position, and this way there is the least dependance within the project regarding the displaying of different game objects.

`Entity` has the following public methods and instance variables:
* Methods:
    * `public Entity entityAfterCollision()`
        * returns the `Entity` after its collision has been handled. The default for this will be `this`, and it will return a different entity if the type is supposed to change based on the collision.
        * marks Entities that should lose a life as 'dead'
    * `public void update()`
        * changes the entity's characteristics (x position, y position, velocities) based on the physics engine.
    * `public void addAbility()`
        * add a new ability to the `Entity`. This will be used when creating the `Entity`, or if the abilities are changed at any point.
    * `public void setIsVisible()`
        * if the game engine determines that an `Entity` is no longer on the visible screen, this method will be called to update the ImageView component.
* Instance variables:
    * `private String IMAGE_FILE_PATH`
    * `private String COLLISION_PROPERTIES_FILE`
    * `private Physics myPhysics`
    * `private ResourceBundle collisionResourceBundle`

This module will implement an interface called `Manageable`. The purpose of `Manageable` is to contain methods for any object that has the ability the interact with another object, and procress diffent collision types of different attributes. 

#### Physics Engine Sub Module
The Entities within the model will consist of their own Physics objects which will be responsible for updating the Entity's personal x and y positions, velocities, and accelerations.

The `Physics` class is broken down into `LandPhysics` and `WaterPhysics` sub-classes and is open to extendability with a greater quantity of custom physics classes, each with any and all unique characteristics that they desire. (For example a `FlyingPhysics`, or `MoonPhysics` class could also be created and used in place of the default `LandPhysics` class)

`Physics` has the following important instance variables and methods:
* Instance Variables:
    * `private float myXPos`
    * `private float myYPos`
    * `private float myXVel`
    * `private float myYVel`
    * `private float myXAccel`
    * `private float myYAccel`
* Important Methods:
    * `public float getXPos()`
    * `public float getYPos()`
    * `public float getXVel()`
    * `public float getYVel()`
    * `public float getXAccel()`
    * `public float getYAccel()`
    * `public void adjustValue(String valType, float newVal)`
      
      
## Example games
### Example 1
When the program is run the user will select a user that has already been created. The information associated with this use (the number of points, lives, and the levels completed) will be stored within a file. Then the user will not change characters and will move to select a level. They will play a level that has already been created. This level will be the most basic level, the character will be able to walk on land, destory bricks, and gain power ups. The user will use the arrow keys to move, and the space bar to jump. The user will collide with an enemy on the side, causing it to to die. When the user dies, a life will be lost. When the user runs out of lives, the user will be able to restart the level or to return to the main screen. Our design will definitiely support this, as described in design details as this is the most basic implementation of our game.  
### Example 2 
When the program is run the user will choose to create a new user. This user will choose to create a new user with a given name. This user will start with 0 points, the 3 lives, and no levels that have been beaten. Then the user will move on to select a character The user will choose to change their character to someone different than Mario. Next, the user will move to choose a level. The user will only have one option in choosing a level. The user will play this level. Half-way through this level, the user will decide that they have to do something, so they choose to pause the level. The user will press the pause button, which will temporarily stop the timeline. The user will then return and enter a pipe that takes them underwater. This will change the way in which the user is able to move, since they will float to the top. This is supported by our design because each character has a physics component that can be altered to contain physics for specific environments. A little later in the game, the user  will decide that they want to exit the game. The user will press the exit button, which will take the user back to the home screen. 

### Example 3 
When the program is run the user will choose to use a user that has already been created. The user will then move the main screen. The user will choose to create their own level. Using different colored squares, the user will place squares onto a grid that specify what will be located at the position on the level that will be created. This grid will then be read into a file to create a file that is similar to the files that can be pre-created to contain level information. Our design supports this concept, as levels would still be read in the same way once the file is written, so there would be no changes to the rest of the program. When the user plays this level, they will destroy bricks by hitting them with their head. The brick will release a powerup which will give the character a new feature, allowing it to have new features. For example, this feature allows the character to shoot fire balls. The character reaches the end of the level and completes the level. This returns the user to the level selector screen, where an animation will display the character walking towards the next level. The amount of coins and lives will be stored and the character will then be able to play the next level, or any level that has already been unlocked. 
  
  
## Design Considerations
1. A design decision that our team discussed was if the Entity should extend ImageView or if there should be seperate classes for the back-end Entity and the front-end Entity. The pro of having the Entity extend ImageView is that then the Entity itself has the ability to contain a coordinate position then can be easily set. The con of having entity extend ImageView and the pro of having tow seperate objects is that if the entity extends ImageView the model is not completly seperated from the view. After discussing with Professor Duvall and a TA (via Piazza) our team decided that in this case, it is okay for the model to not be completely seperated from the view, since it improves the overall code quality and readability. Therefore, we have decided to have the entity extend ImageView. 

2. A design decision that was directly related to the one stated above was where Collision Detection should take place. We debated if the CollionManager be looking for collisions between the images themselves or for collisions between hit boxes that can be created. The downside of having the collisions be checked between the ImageView objects is that all ImageView objects are rectangles, so a collision may be detected even if visually two objects due not intersect. As a result, we decided to detect for Collisions between a hitbox. 

3. A design decision that was our team discussed was whether each Entity should have access to the EntityManager. This was debated because sometimes a collision results in the creation of a new Entity. The debate was whether the Entity should be able to access the method in EntityManager to add this new Entity to the EntityList, or if the EntityManager receive an Entity object after it passes the Entity a collision object. It was decided that all of the Entity classes should not have access to the EntityManager, because they should not be able to freely add or remove entities. It makes more sense for a new Entity to be a return value that the EntityManager can check to see if this entity is already created in the EntityList. This allows our design to be more closed, since the Entities do not have access to the EntityManager. 