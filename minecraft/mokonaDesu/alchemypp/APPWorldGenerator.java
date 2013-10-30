package mokonaDesu.alchemypp;

import java.util.Random;

import mokonaDesu.alchemypp.blocks.BlockRegistry;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class APPWorldGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		if (AlchemyPP.generateOrichalcum) {
		//Orichalcum generation
		int iMax = 3;
		if (world.getBiomeGenForCoords(chunkX*16, chunkZ*16) instanceof BiomeGenOcean)
			iMax = 8;
		for (int i = 0; i < iMax; i++) {
			int y = random.nextInt(15) + 1;
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			
			new WorldGenMinable(BlockRegistry.appBlockOrichalcumOre.blockID, random.nextInt(10) + 1).generate(world, random,16 * chunkX + x, y,16 * chunkZ + z);
		}

		}
	}

}
