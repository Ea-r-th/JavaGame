package chunkGeneration;

import java.util.Random;

public class HeightsGenerator {

    private static float amplitude = 10f;

    private Random random = new Random();
    private static int seed;
    private static int octaves = 3;
    private static float roughness = 0.5f;

    private int xOffset = 0;
    private int zOffset = 0;

    public HeightsGenerator(){
        this.seed = random.nextInt(1000000000);
    }

    public float generateHeights(int x, int z) {
        float total = 0;
        float d = (float) Math.pow(2, octaves-1);
        for(int i=0;i<octaves;i++){
            float freq = (float) (Math.pow(2, i) / d);
            float amp = (float) Math.pow(roughness, i) * amplitude;
            total += getIntermediateNoise((x+xOffset)*freq, (z + zOffset)*freq) * amp;
        }
        return total;
    }

    public float getNoise(int x, int z){
        random.setSeed(x * 98742 + z * 54238 + seed);
        return random.nextFloat() * 2f - 1f;
    }

    private float getAveragedNoise(int x, int z){
        float sides = (getNoise(x,z-1) + getNoise(x+1,z) + getNoise(x, z+1) + getNoise(x-1, z)) / 7f;
        float corners = (getNoise(x-1, z-1) + getNoise(x-1,z+1) + getNoise(x+1, z-1) + getNoise(x+1, z+1) / 14f);
        float center = getNoise(x,z) / 3f;
        return (sides + center + corners);
    }

    private float cosInterpolate(float a, float b, float blendFactor){
        double x = blendFactor * Math.PI;
        float f = (float)(1 - Math.cos(x)) * 0.5f;
        return a * (1 - f) + b * f;
    }

    public float getIntermediateNoise(float x, float z){
        int intX = (int) x;
        float fX = x - intX;
        int intZ = (int) z;
        float fZ = z - intZ;

        float v1 = getAveragedNoise(intX, intZ);
        float v2 = getAveragedNoise(intX + 1, intZ);
        float v3 = getAveragedNoise(intX, intZ + 1);
        float v4 = getAveragedNoise(intX + 1, intZ + 1);
        float i1 = cosInterpolate(v1,v2, fX);
        float i2 = cosInterpolate(v3,v4, fZ);

        return cosInterpolate(i1,i2,fZ);
    }

    public int getSeed() {
        return seed;
    }

    public static float getAmplitude() {
        return amplitude;
    }
}
