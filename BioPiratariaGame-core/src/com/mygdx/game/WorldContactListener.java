package com.mygdx.game;

import Sprites.Enemy;
import Sprites.Heroi;
import Sprites.Vilao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;


public class WorldContactListener implements ContactListener {

	private Heroi heroi;
	private PlayScreen screen;
	private Vilao vilao;
	
	public WorldContactListener(Heroi heroi,PlayScreen screen,Vilao vilao) {
		this.heroi = heroi;
		this.screen = screen;
		this.vilao = vilao;
	}
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
        	BioPirataria.manager.get("Songs/sfx_movement_jump2.wav", Sound.class).play();
        	break;
        case BioPirataria.TERRAIN_BIT | BioPirataria.HEROI_BIT:
        	Gdx.app.log("Corpo encostou no terreno. - Inicio", "");
        	Hud.lostLife(10);
        	BioPirataria.manager.get("Songs/sfx_movement_jump2.wav", Sound.class).play();
        	break;
        case BioPirataria.ENEMY_BIT | BioPirataria.BORDAS_BIT:
        	//Vilao.lostVida(10);
        	Gdx.app.log("Enemy encostou na borda. - Inicio \nVida: "+Vilao.getVida(), "");
            Vilao.reverseVelocity(true,false);
        	break;
        case BioPirataria.HEROI_BIT | BioPirataria.ENEMY_BIT:
        	Gdx.app.log("Heroi perdeu vida por causa do vilão", "Morreu");
        	Hud.lostLife(80);
        	BioPirataria.manager.get("Songs/sfx_movement_jump2.wav", Sound.class).play();
        	heroi.afastaheroi();
        	break;
        case BioPirataria.TIRO_BIT | BioPirataria.TERRAIN_BIT:
        	if(fixA.getFilterData().categoryBits == BioPirataria.TIRO_BIT) {
        	//((Tiro)fixA.getUserData()).Destroytiro(((Tiro)fixA.getUserData()).b2body);
        		((Tiro)fixA.getUserData()).setToDestroy();
        		
        	}else {
        		//((Tiro)fixB.getUserData()).Destroytiro(((Tiro)fixB.getUserData()).b2body);
        		 ((Tiro)fixB.getUserData()).setToDestroy();
        	}
        	Gdx.app.log("Bala encostou no terreno", "");
        	break;
        case BioPirataria.TIRO_BIT | BioPirataria.ENEMY_BIT:
        	if(fixA.getFilterData().categoryBits == BioPirataria.TIRO_BIT) {
        		((Tiro)fixA.getUserData()).setToDestroy();
        		Vilao.reverseVelocity(true,false);
        		Hud.addPontos(30);
        	}else {
        		((Tiro)fixB.getUserData()).setToDestroy();
        		Vilao.reverseVelocity(true,false);
        		Hud.addPontos(30);
        	}
        	Vilao.lostVida(30);
        	Gdx.app.log("Bala encostou no Inimigo", "");
        	break;
        case BioPirataria.TIROENEMY_BIT | BioPirataria.HEROI_BIT:
        	if(fixA.getFilterData().categoryBits == BioPirataria.TIROENEMY_BIT) {
        		Hud.lostLife(10);
        		Gdx.app.log("Levou um tirou do inimigo", "");
        		((Tiro)fixA.getUserData()).setToDestroy();
        	}else {
        		Hud.lostLife(10);
        		Gdx.app.log("Levou um tirou do inimigo", "");
        		((Tiro)fixB.getUserData()).setToDestroy();
        	}
        case BioPirataria.TIROENEMY_BIT | BioPirataria.TERRAIN_BIT:
        	if(fixA.getFilterData().categoryBits == BioPirataria.TIROENEMY_BIT) {
        		Gdx.app.log("tiro inimigo pegou no terreno", "");
        		((Tiro)fixA.getUserData()).setToDestroy();
        	}else {
        		Gdx.app.log("tiro inimigo pegou no terreno", "");
        		((Tiro)fixB.getUserData()).setToDestroy();
        	}
        case BioPirataria.HEROI_BIT | BioPirataria.AGUA_BIT:
        	screen.velocity = 2;
        	Gdx.app.log("Heroi encostou na agua" + screen.velocity, "");
        	BioPirataria.manager.get("Songs/ocean3.mp3", Sound.class).play();
        	break;
        case BioPirataria.HEROI_BIT | BioPirataria.LAVA_BIT:
        	//Hud.lostLife(1);
        	screen.heroiLava = true;
        	Gdx.app.log("Heroi encostou na lava", "");
        	BioPirataria.manager.get("Songs/Fogo.mp3", Sound.class).play();
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
        case BioPirataria.HEROI_BIT | BioPirataria.AGUA_BIT:
        	screen.velocity = 80;
        	Gdx.app.log("Heroi saiu da agua" + screen.velocity, "");
        	break;
        case BioPirataria.HEROI_BIT | BioPirataria.LAVA_BIT:
        	//Hud.lostLife(1);
        	screen.heroiLava= false;
        	Gdx.app.log("Heroi saiu da lava", "");
        	break;
        }
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		
	}

}
