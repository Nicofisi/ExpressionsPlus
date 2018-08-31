package de.jaylawl.expressionsplus.elements.expressions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprSpawnRange extends SimplePropertyExpression<Block, Number> {

    static {
        register(ExprSpawnRange.class, Number.class, "[entity( |-)]spawn range", "blocks");
    }

    @Override
    @Nullable
    public Number convert(Block block) {
        if (block.getType() != Material.SPAWNER)
            return null;
        BlockState state = block.getState();
        CreatureSpawner spawner = (CreatureSpawner) state;
        return spawner.getSpawnRange();
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.SET || mode == ChangeMode.RESET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        int value = delta == null ? -1 : ((Number) delta[0]).intValue();

        for (Block block : getExpr().getArray(e)) {
            if (block.getType() == Material.SPAWNER) {
                BlockState state = block.getState();
                CreatureSpawner spawner = (CreatureSpawner) state;
                switch (mode) {
                    case ADD:
                        spawner.setSpawnRange(spawner.getSpawnRange() + value);
                        break;
                    case REMOVE:
                        spawner.setSpawnRange(spawner.getSpawnRange() - value);
                        break;
                    case SET:
                        spawner.setSpawnRange(value);
                        break;
                    case RESET:
                        spawner.setSpawnRange(4);
                        break;
                    default:
                        assert false;
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "entity spawn range";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

}