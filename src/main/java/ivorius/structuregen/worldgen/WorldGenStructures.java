/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.structuregen.worldgen;

import cpw.mods.fml.common.IWorldGenerator;
import ivorius.structuregen.StructureGen;
import ivorius.structuregen.ivtoolkit.math.AxisAlignedTransform2D;
import ivorius.structuregen.ivtoolkit.blocks.BlockCoord;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 24.05.14.
 */
public class WorldGenStructures implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if (world.getWorldInfo().isMapFeaturesEnabled())
        {
            BiomeGenBase biomeGen = world.getBiomeGenForCoords(chunkX * 16 + 16, chunkZ * 16 + 16);

            StructureSelector structureSelector = StructureHandler.getStructureSelectorInBiome(biomeGen);
            List<StructureInfo> generated = structureSelector.generatedStructures(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);

            for (StructureInfo info : generated)
            {
                int genX = chunkX * 16 + random.nextInt(16);
                int genZ = chunkZ * 16 + random.nextInt(16);
                int genY = generateStructureRandomly(world, random, info, genX, genZ);
                
                StructureGen.logger.info("Generated " + info + " at " + genX + ", " + genY + ", " + genZ);
            }
        }
    }

    public static int generateStructureRandomly(World world, Random random, StructureInfo info, int x, int z)
    {
        int[] size = info.structureBoundingBox();
        int genY = info.generationY(world, random, x, z);

        AxisAlignedTransform2D transform = AxisAlignedTransform2D.transform(info.isMirrorable() ? world.rand.nextInt(4) : 0, info.isMirrorable() && random.nextBoolean());

        int genX = x - size[0] / 2;
        int genZ = z - size[2] / 2;

        info.generate(world, random, new BlockCoord(genX, genY, genZ), transform, 0);

        return genY;
    }
}
