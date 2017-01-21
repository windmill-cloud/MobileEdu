package space.firstorder.hc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;




public class Main extends ApplicationAdapter {
	public SpriteBatch sprites;
	public Environment environment;

	public PerspectiveCamera cam;

	public ModelBatch modelBatch;
	public ModelInstance instance;
	public Model model;

	public Texture logo;
	public Music backgroundMusic;

	/*
		add variables here if you need any, in the case you're doing
		texturing or something more complicated
	*/

	@Override
	public void create() {
		// TODO: create completely new batches for sprites and models
		sprites = new SpriteBatch();
		modelBatch = new ModelBatch();

		// TODO: create a new environment
		// set a new color attribute for ambient light in the environment
		// add a new directional light to the environment

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		// create a new logo texture from the "data/firstorder.png" file
		logo = new Texture("data/firstorder.png");

		// TODO: create a new perspective camera with a field-of-view of around 70,
		//  and the width and height found in the Gdx.graphics class
		// set the position of the camera to (100, 100, 100)
		// set the camera to look at the origin point (0, 0, 0)
		// set the near and far planes of the camera to 1 and 300
		// update the camera
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		cam = new PerspectiveCamera(70f, width, height);
		cam.position.set(100f,100f,100f);
		cam.lookAt(0f, 0f, 0f);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("data/StarWarsMusicTheme.mp3"));
		backgroundMusic.setLooping(true);
		backgroundMusic.play();

		// create a new model loader
		final ModelLoader modelLoader = new ObjLoader();

		// TODO: load the internal file "data/stormtrooper.obj" into the model variable
		model = modelLoader.loadModel(Gdx.files.internal("data/stormtrooper_unwrapped.obj"));


		// TODO: create a new model instance and scale it to 20% it's original size (it's huge...)
        instance = new ModelInstance(model); // ← our model instance is here
		instance.transform.scale(0.2f, 0.2f, 0.2f);

		// TODO: set the helmet details material to a new diffuse black color attribute
        getHelmetDetails().material = new Material(ColorAttribute.createDiffuse(Color.BLACK));
        getHelmetMoreDetails().material = new Material(ColorAttribute.createDiffuse(Color.DARK_GRAY));
        getHelmetBase().material = new Material(ColorAttribute.createDiffuse(Color.WHITE));
        // set the input processor to work with our custom input:
        //  clicking the image in the lower right should change the colors of the helmets
        //  bonus points: implement your own GestureDetector and an input processor based on it
        Gdx.app.log("instance node size", ""+String.valueOf(instance.nodes.size));


		Gdx.input.setInputProcessor(new FirstOrderInputProcessor(cam, new Runnable() {
            private Texture camouflage =  new Texture("data/camouflage.png");
            private Texture paper =  new Texture("data/paper.png");
            private Texture hive =  new Texture("data/hive.png");
            private Texture strips =  new Texture("data/strip.png");
            private Texture grass =  new Texture("data/grass.jpeg");
            public void run() {
				// TODO: change the helmet details material to a new diffuse random color

				getHelmetDetails().material = getRandomMaterial();
				getHelmetMoreDetails().material = getRandomMaterial();

                // bonus points:
                //  randomly change the material of the helmet base to a texture
                //  from the files aloha.png and camouflage.png (or add your own!)
				getHelmetBase().material = getRandomMaterial();

			}

            private Material getRandomMaterial() {
                int rand = (int) (MathUtils.random() * 100) % 8;
                Gdx.app.log("Random", "" + rand);

                //Texture aloha =  new Texture("data/aloha.png");
                Material randMaterial = null;

                switch (rand) {
                    case 0:
                    case 1:
                        randMaterial = new Material(ColorAttribute.createReflection(Color.WHITE));
                        break;
                    case 2:
                        randMaterial = new Material(TextureAttribute.createDiffuse(paper));
                        break;
                    case 3:
                        randMaterial = new Material(TextureAttribute.createDiffuse(hive));
                        break;
                    case 4:
                        randMaterial = new Material(TextureAttribute.createDiffuse(camouflage));
                        break;
                    case 5:
                        randMaterial = new Material(ColorAttribute.createDiffuse(getRandomColor()));
                        break;
                    case 6:
                        randMaterial = new Material(TextureAttribute.createDiffuse(strips));
                        break;
                    case 7:
                        randMaterial = new Material(TextureAttribute.createDiffuse(grass));
                        break;
                }
                return randMaterial;
            }
		}));
	}

	@Override
	public void render() {
		// create a new viewport of size (0, 0, width, height)
		//	the width and height you can get from the Gdx.graphics
        // clear the color and depth buffer
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


		// TODO: begin the model batch with the current camera
		// render the instance of the model in the set-up environment using the model batch
        // end the model batch rendering process
		modelBatch.begin(cam);
		modelBatch.render(instance, environment);

		modelBatch.end();

		// TODO: begin the sprite batch rendering
		// draw the new order logo at (50, 50, 200, 200)
		// end the sprite batch rendering process
        sprites.begin();
		sprites.draw(logo,50, 50, 200, 200);
		sprites.end();
	}

	@Override
	public void dispose () {
		// TODO: dispose of the model and sprite batch
		modelBatch.dispose();
		sprites.dispose();
		// TODO: dispose of the model
		model.dispose();
		backgroundMusic.dispose();
	}

	/*
		play with this if you want to make more colorful helmets
	 */
	protected NodePart getHelmetMoreDetails() {
		return instance.nodes.get(0).parts.get(0);
	}

	protected NodePart getHelmetDetails() {
		return instance.nodes.get(1).parts.get(0);
	}

    protected NodePart getHelmetBase() {
        return instance.nodes.get(2).parts.get(0);
    }


	protected Color getRandomColor() {
		return new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), MathUtils.random());
	}
}
