/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.reccomplex.gui.worldscripts.command;

import ivorius.ivtoolkit.tools.IvTranslations;
import ivorius.reccomplex.gui.RCGuiTables;
import ivorius.reccomplex.gui.table.*;
import ivorius.reccomplex.scripts.world.WorldScriptCommand;
import net.minecraft.init.Blocks;

/**
 * Created by lukas on 05.06.14.
 */
public class TableDataSourceSpawnCommandEntry extends TableDataSourceSegmented
{
    private WorldScriptCommand.Entry entry;

    private TableDelegate tableDelegate;

    public TableDataSourceSpawnCommandEntry(WorldScriptCommand.Entry entry, TableDelegate tableDelegate)
    {
        this.entry = entry;
        this.tableDelegate = tableDelegate;
    }

    @Override
    public int numberOfSegments()
    {
        return 1;
    }

    @Override
    public int sizeOfSegment(int segment)
    {
        return 3;
    }

    @Override
    public TableElement elementForIndexInSegment(GuiTable table, int index, int segment)
    {
        if (index == 0)
        {
            TableCellPresetAction cell = new TableCellPresetAction("default", IvTranslations.get("reccomplex.gui.apply"),
                    new TableCellButton("", "spawner", Blocks.MOB_SPAWNER.getLocalizedName()),
                    new TableCellButton("", "entity", IvTranslations.get("reccomplex.spawncommand.preset.entity"))
            );
            cell.addAction(action -> {
                if ("spawner".equals(action))
                    entry.command = "/setblock ~ ~ ~ mob_spawner 0 replace {SpawnData:{id:Zombie}}";
                else if ("entity".equals(action))
                    entry.command = "/summon Zombie ~ ~ ~";

                tableDelegate.reloadData();
            });
            return new TableElementCell(IvTranslations.get("reccomplex.preset"), cell);
        }
        else if (index == 1)
        {
            TableCellString cell = new TableCellString("command", entry.command);
            cell.setMaxStringLength(32767); // Same as GuiCommandBlock.
            cell.addPropertyConsumer(val -> entry.command = val);
            return new TableElementCell(IvTranslations.get("reccomplex.gui.command"), cell);
        }
        else if (index == 2)
        {
            return RCGuiTables.defaultWeightElement(val -> entry.weight = TableElements.toDouble(val), entry.weight);
        }

        return null;
    }
}
