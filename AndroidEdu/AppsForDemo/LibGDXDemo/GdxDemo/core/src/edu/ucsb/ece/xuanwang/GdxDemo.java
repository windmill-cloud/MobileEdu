package edu.ucsb.ece.xuanwang;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class GdxDemo extends ApplicationAdapter {
    public Environment environment;
    public PerspectiveCamera cam;
    public ModelBatch modelBatch;
    public ModelInstance instance;
    public Model model;

    @Override
    public void create() {
        modelBatch = new ModelBatch();

        // create a new environment
        environment = new Environment();

        // set a new color attribute for ambient light in the environment
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 0.1f));

        // add a new directional light to the environment
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));


        // create a new perspective camera with a field-of-view of around 70,
        // and the width and height found in the Gdx.graphics class
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

        model = new ModelBuilder().createBox(300f, 300f, 300f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model); // ← our model instance is here
        instance.transform.scale(0.2f, 0.2f, 0.2f);

        Gdx.input.setInputProcessor(new DemoInputProcessor(cam, new Runnable() {

            public void run() {
                // TODO: custom behavior for touching event

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


        // begin the model batch with the current camera
        // render the instance of the model in the set-up environment using the model batch
        // end the model batch rendering process
        modelBatch.begin(cam);
        modelBatch.render(instance, environment);
        modelBatch.end();
    }

    @Override
    public void dispose () {
        // dispose of the model and sprite batch
        modelBatch.dispose();
        // dispose of the model
        model.dispose();
    }
}

