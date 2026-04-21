import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.EnumMap;
import java.util.Map;

public class SceneManager {

    private static SceneManager instance;

    private final Stage stage;
    private final Map<SceneType, Scene> cache = new EnumMap<>(SceneType.class);

    private SceneManager(Stage stage) {
        this.stage = stage;
    }

    public static void init(Stage stage) {
        if (instance == null) {
            instance = new SceneManager(stage);
        }
    }

    public static SceneManager getInstance() {
        if (instance == null) {
                throw new IllegalStateException("SceneManager not initialised");
        }
        return instance;
    }

    /**
     * Navigates to a Scene, if a scene of that type
     * is stored in the cache, then it uses the scene
     * from the cache. Otherwise, it creates a new scene.
     * @param type The type of scene to be navigated to.
     */
    public void navigateTo(SceneType type) {
        Scene scene = null;
        if (cache.containsKey(type)) {
            scene = cache.get(type);
        }
        else {
            scene = SceneFactory.create(type, stage);
        }
        stage.setScene(scene);
    }

    /**
     * Stores a scene into the cache.
     * @param type The type of scene
     * @param scene A scene
     */
    public void cache(SceneType type, Scene scene) {
        cache.put(type,scene);
    }

    /**
     * Removes a scene from the cache
     * @param type The scene type to be removed
     */
    public void uncache(SceneType type) {
        cache.remove(type);
    }

    /**
     * Removes all scenes from the cache.
     */
    public void uncacheAll(){
        cache.clear();
    }
}
