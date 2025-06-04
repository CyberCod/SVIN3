package com.smiths;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.util.math.MathUtils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape.Type;

public class Box2D {
	// make physics world
	public PhysicsWorld mPhysicsWorld;
	
	// make a scene for local reference
	
	private Scene mScene;
	//list of horizontal platforms 
	
	ArrayList<Rectangle> platforms;
	
	//list of vertical walls
	ArrayList<Rectangle> walls;
	
	//engine for local reference
	Engine mEngine;

	
	//Constructor called from main activity after all of the variables have been created in main activity
	public Box2D(ArrayList<Rectangle> horizontals, ArrayList<Rectangle> verticals,  Engine engine, Scene scene)
	{
		//making main activity's vectors locally adressable
		mEngine = engine;
		
		mScene = scene;
		
		this.platforms = horizontals;
		
		this.walls = verticals;

		this.mEngine.registerUpdateHandler(new FPSLogger());

		//vector for gravity
		Vector2 GravityVector = new Vector2(0,10);
		
		//instantiate the physics world, with gravity
		this.mPhysicsWorld = new PhysicsWorld(GravityVector, true);
		
		//recycle the gravity vector (basic cleanup)
		Vector2Pool.recycle(GravityVector);
		
		//Fixturedef created and assigned
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 2f);
		
		//all of our horizontals are added to the physics world
		for(int x = 0;x<platforms.size();x++)
		{
			
			Body newbody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, platforms.get(x), BodyType.StaticBody, wallFixtureDef);
			newbody.setUserData("platform");
		}
		
		//all of our verticals are added to the physics world
		for(int x = 0;x<walls.size();x++)
		{
			Body newbody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, walls.get(x), BodyType.StaticBody, wallFixtureDef);
			newbody.setUserData("wall");
		}
		
		//create contact listener
		mPhysicsWorld.setContactListener(createContactListener());
		
		this.mScene.registerUpdateHandler(this.mPhysicsWorld);
	}

	
	// ===========================================================
	// Methods
	// ===========================================================

	
	
	/*
	 *    addObject - Adds rectangle to physics world
	 */
	public void addObject(Rectangle object, int shape, State state, String identifier, boolean rotatable, boolean bullet, boolean active, GameObject recursive, BodyType type) {
		Body body = null;
		
		final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 2f);

		if(shape == 1) //box objects
			{
			body = PhysicsFactory.createBoxBody(this.mPhysicsWorld, object, type, objectFixtureDef);
			}
		else if(shape == 2) //round objects
			{
				body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, object, type, objectFixtureDef);
			}
		
		PhysicsConnector phCon = new PhysicsConnector(object, body, false, false);
		phCon.setUpdatePosition(active);
		phCon.setUpdateRotation(active);
		
		
		if(state != null)
		{
			body.setUserData(state);
		}
		else
		{
			body.setUserData(identifier);
		}
		
		this.mPhysicsWorld.registerPhysicsConnector(phCon);
		//object.setUserData(body);
		body.setActive(active);
		body.setFixedRotation(rotatable);
		body.setBullet(bullet);
		body.setSleepingAllowed(true);
		this.mScene.attachChild(object);
	}

	
	

	public void addObject(Rectangle object, int type, String identifier, boolean fixedrotation, boolean bullet) {
		Body body = null;

		final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 2f);

		if(type == 1) //box objects
			{
			body = PhysicsFactory.createBoxBody(this.mPhysicsWorld, object, BodyType.DynamicBody, objectFixtureDef);
			}
		else if(type == 2) //round objects
			{
				body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, object, BodyType.DynamicBody, objectFixtureDef);
			}
		body.setUserData(identifier);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(object, body, true, true));
		object.setUserData(body);
		body.setFixedRotation(fixedrotation);
		body.setBullet(bullet);
		body.setSleepingAllowed(true);
		
		this.mScene.attachChild(object);
	}

	
	
	
	
	public void addArrow(Arrow arrow, int type, String identifier, boolean fixedrotation, boolean bullet) {
		Body body = null;

		final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.1f, .2f);

		body = PhysicsFactory.createBoxBody(this.mPhysicsWorld, arrow, BodyType.DynamicBody, objectFixtureDef);
		
		body.setUserData(identifier);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(arrow, body, true, true));
		arrow.setUserData(body);
		arrow.physicsBody = body;
		body.setFixedRotation(fixedrotation);
		body.setBullet(bullet);
		body.setSleepingAllowed(true);
		
		this.mScene.attachChild(arrow);
	}
	
	
	
	
	
	

/*
 * 		hit - gives a physics body a vector based on two integers
 */
	
	 public void hit(final GameObject defender, int hitvelocity_x, int hitvelocity_y) {
	
		 Body objectBody = defender.physicsBody;
		final Vector2 velocity = new Vector2(hitvelocity_x, hitvelocity_y);
		objectBody.setLinearVelocity(velocity);
		
	}

	 public void hit(final Rectangle object, int hitvelocity_x, int hitvelocity_y) {
		final Body objectBody = (Body)object.getUserData();

		final Vector2 velocity = Vector2Pool.obtain(hitvelocity_x, hitvelocity_y);
		objectBody.setLinearVelocity(velocity);
		Vector2Pool.recycle(velocity);
	}

	 
	
	 public void TransformToBox2d(GameObject sprite)
	 {
		 final float widthD2 = sprite.getWidth() / 2;

		 final float heightD2 = sprite.getHeight() / 2;

		 float xpos = sprite.xpos;
		 
		 float ypos = sprite.ypos;
		 
		 final float angle =  sprite.physicsBody.getAngle(); // keeps the body angle

		 final Vector2 v2 = new Vector2((xpos + widthD2) / 32, (ypos + heightD2) / 32);

		 sprite.physicsBody.setTransform(v2, angle);

		 Vector2Pool.recycle(v2);
	 }
	 
	 public void TransformFromBox2d(GameObject sprite)
	 {
		 final float widthD2 = sprite.getWidth() / 2;

		 final float heightD2 = sprite.getHeight() / 2;

		 
		 sprite.xpos = (sprite.physicsBody.getWorldCenter().x * 32)-widthD2;
		 
		 sprite.ypos = (sprite.physicsBody.getWorldCenter().y * 32)-heightD2;

		 sprite.setRotation(0);
		 sprite.setPosition(sprite.xpos, sprite.ypos);

	 }

	 
	 



	 public void Transform(GameObject sprite, float xpos, float ypos)
	 {
		 final float widthD2 = sprite.getWidth() / 2;

		 final float heightD2 = sprite.getHeight() / 2;

		 final float angle =  sprite.physicsBody.getAngle(); // keeps the body angle

		 final Vector2 v2 = new Vector2((xpos + widthD2) / 32, (ypos + heightD2) / 32);

		 sprite.physicsBody.setTransform(v2, angle);

		 Vector2Pool.recycle(v2);
	 }
	 
	 private ContactListener createContactListener()

	    {
	        ContactListener contactListener = new ContactListener()
	        {
	            @Override
	            public void beginContact(Contact contact)
	            {

	                final Fixture x1 = contact.getFixtureA();

	                final Fixture x2 = contact.getFixtureB();

	                //SetCollision(x1, x2, contact, true);
	            }

	 

	            @Override

	            public void endContact(Contact contact)

	            {
	                final Fixture x1 = contact.getFixtureA();

	                final Fixture x2 = contact.getFixtureB();
	                
	                //SetCollision(x1, x2, contact, false);
	            }

	 

	            @Override

	            public void preSolve(Contact contact, Manifold oldManifold)

	            {
	            	 final Fixture x1 = contact.getFixtureA();

	                final Fixture x2 = contact.getFixtureB();
	                // variable to handle bodies y position
	                if(CheckContactRules(x1, x2) == false)
	                {
	                	contact.setEnabled(false);
	                }
	                else
	                {
	                	contact.setEnabled(true);
	                }
	            }

	 

	            @Override

	            public void postSolve(Contact contact, ContactImpulse impulse)

	            {

	                   

	            }

	        };

	        return contactListener;

	    }

	
	 /*
	  * This method allows me to check both iterations of my ContactRules method without having to type it all twice
	  */
	 Boolean CheckContactRules(Fixture object1, Fixture object2)
	 {
		 
		 if((ContactRules(object1, object2) == true)||(ContactRules(object2, object1) == true))
		 {
			 return true;
		 }
		 
		 return false;
	 }
	 
	 String getRole(Fixture object)
	 {
		 State state;
		 String role;
		 if((State)object.getUserData() != null)
		 {
		    state = (State) object.getUserData();
		    role = state.role;
		 }
		 else if((String)object.getUserData()!= null)
		 {
			 role = (String)object.getUserData();
		 }
		 else
		 {
			 role = "undefined";
		 }
	
		 return role;
	 }

	 Boolean ContactRules(Fixture object1, Fixture object2)
	 {
		 if (object1.getBody().getUserData().equals("player")&& object2.getBody().getUserData().equals("box"))
		 {
			 return false;
	
		 }
		 
		/* if (object1.getBody().getUserData().equals("arrow")&& object2.getBody().getUserData().equals("ninja"))
		 {
			 return true;
	
		 }*/

		 if (object1.getBody().getUserData().equals("arrow")&& object2.getBody().getUserData().equals("wall"))
		 {
			 return true;
	
		 }
		 
		 if (object1.getBody().getUserData().equals("arrow")&& object2.getBody().getUserData().equals("platform"))
		 {
			 return true;
	
		 }
		 
		 if (object1.getBody().getUserData().equals("box")&& object2.getBody().getUserData().equals("box"))
		 {
			 return true;
	
		 }
		 
		 if (object1.getBody().getUserData().equals("box")&& object2.getBody().getUserData().equals("wall"))
		 {
			 return true;
	
		 }
		 if (object1.getBody().getUserData().equals("platform")&& object2.getBody().getUserData().equals("box"))
		 {
			 final float object1y = object1.getBody().getPosition().y * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
             final float object2y = object2.getBody().getPosition().y * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

			 
			 if(object1y < object2y)
			 {
				 return false;
			 }
			 else
			 {
				 return true;
			 }
	
		 }

		 return false;
		 
		 
	 }

	 
	 
	 
	 //EOF
}
