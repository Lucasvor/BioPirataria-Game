package Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BioPirataria;
import com.mygdx.game.PlayScreen;

public class B2WorldCreator {
	public B2WorldCreator(PlayScreen screen) {
		World world = screen.getWorld();
		TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
// local onde aplica as coli�es no mapa.
        //for(int i = 1;i <= 3;i++) {
        // objetos do mapa
        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() /2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape = shape;
            fdef.filter.categoryBits = BioPirataria.TERRAIN_BIT;
            body.createFixture(fdef).setUserData("Terreno");
            
        }
        // borda do jogo
        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() /2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape = shape;
            fdef.filter.categoryBits = BioPirataria.BORDAS_BIT;
            body.createFixture(fdef).setUserData("Terreno");
            
        }
	
 }
}
