package com.mygdx.game;

import Sprites.Enemy;
import Sprites.Vilao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;


public class WorldContactListener implements ContactListener {

	public static boolean tiroContato;
	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
//		Fixture fixA = contact.getFixtureA();
//		Fixture fixB = contact.getFixtureB();
//		if(fixA.getUserData().toString().equals("Corpo") || fixB.getUserData().toString().equals("Corpo")){
//		Fixture head = fixA.getUserData() == "Corpo" ? fixA:fixB;
//		Fixture object = head == fixA? fixB: fixA;
//		
//		if(object.getUserData() != null) {
//			Gdx.app.log("Corpo encostou no terreno. - Inicio", "");
//			Hud.lostLife(10);
//		}
//		}else {
//			Gdx.app.log("Head encostou no terreno. - Inicio", "");
//		}
		Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        Gdx.app.log("Numero "+cDef, "");
        switch (cDef){
        case BioPirataria.BORDAS_BIT | BioPirataria.HEROI_BIT:
        	Gdx.app.log("Corpo encostou na borda. - Inicio", "");
        	Hud.lostLife(10);
        	break;
        case BioPirataria.TERRAIN_BIT | BioPirataria.HEROI_BIT:
        	Gdx.app.log("Corpo encostou no terreno. - Inicio", "");
        	Hud.lostLife(10);
        	break;
        case BioPirataria.ENEMY_BIT | BioPirataria.BORDAS_BIT:
        	Gdx.app.log("Enemy encostou na borda. - Inicio", "");
            Vilao.reverseVelocity(true,false);
        	break;
        case BioPirataria.HEROI_BIT | BioPirataria.ENEMY_BIT:
        	Gdx.app.log("Heroi perdeu vida por causa do vilão", "Morreu");
        	Hud.lostLife(80);
        case BioPirataria.TIRO_BIT | BioPirataria.TERRAIN_BIT:
        	Gdx.app.log("Bala encostou no terreno", "");
        	tiroContato = true;
        	break;
        }
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        Gdx.app.log("Numero "+cDef, "");
        switch (cDef){
        case BioPirataria.BORDAS_BIT | BioPirataria.HEROI_BIT:
        	Gdx.app.log("Corpo saiu da borda. - Inicio", "");
        	break;
        case BioPirataria.TERRAIN_BIT | BioPirataria.HEROI_BIT:
        	Gdx.app.log("Corpo saiu do terreno. - Inicio", "");
        	break;
        case BioPirataria.ENEMY_BIT | BioPirataria.BORDAS_BIT:
        	Gdx.app.log("Enemy saiu da borda. - Inicio", "");
        	break;
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
