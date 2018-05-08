package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;


public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		if(fixA.getUserData().toString().equals("Corpo") || fixB.getUserData().toString().equals("Corpo")){
		Fixture head = fixA.getUserData() == "Corpo" ? fixA:fixB;
		Fixture object = head == fixA? fixB: fixA;
		
		if(object.getUserData() != null) {
			Gdx.app.log("Corpo encostou no terreno. - Inicio", "");
			Hud.lostLife(10);
		}
		}else {
			Gdx.app.log("Head encostou no terreno. - Inicio", "");
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		if(fixA.getUserData().toString().equals("Corpo") || fixB.getUserData().toString().equals("Corpo")){
		Fixture head = fixA.getUserData() == "Corpo" ? fixA:fixB;
		Fixture object = head == fixA? fixB: fixA;
		
		if(object.getUserData() != null)
			Gdx.app.log("Corpo saiu do terreno. - Fim", "");
		}else {
			Gdx.app.log("Head saiu do terreno. - Fim", "");
		}
		
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}

}
