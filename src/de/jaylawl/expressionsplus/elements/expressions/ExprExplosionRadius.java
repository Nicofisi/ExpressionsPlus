package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Creeper;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprExplosionRadius extends SimplePropertyExpression<Entity, Number> {

    static {
        register(ExprExplosionRadius.class, Number.class, "explosion (radius|size)", "entity");
    }

    @Override
    @Nullable
    public Number convert(Entity entity) {
        if (entity.getType() == EntityType.CREEPER) {
            return ((Creeper) entity).getExplosionRadius();
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.SET || mode == ChangeMode.RESET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event event, Object[] delta, ChangeMode mode){
        if ((delta != null) && (getExpr().getSingle(event) != null)) {
            Entity entity = getExpr().getSingle(event);
            if (entity.getType() == EntityType.CREEPER) {
                Creeper creeper = ((Creeper) entity);
                Integer v = ((Number) delta[0]).intValue();
                Integer cur = creeper.getExplosionRadius();
                switch (mode) {
                    case ADD:
                        creeper.setExplosionRadius(Math.max(0, cur + v));
                        break;
                    case REMOVE:
                        creeper.setExplosionRadius(Math.max(0, cur - v));
                        break;
                    case SET:
                        creeper.setExplosionRadius(Math.max(0, v));
                        break;
                    case RESET:
                        creeper.setExplosionRadius(3);
                        break;
                    default:
                        assert false;
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "explosion radius";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
}
