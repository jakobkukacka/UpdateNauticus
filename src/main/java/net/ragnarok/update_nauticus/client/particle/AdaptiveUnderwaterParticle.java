package net.ragnarok.update_nauticus.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;

public class AdaptiveUnderwaterParticle extends TextureSheetParticle {
    AdaptiveUnderwaterParticle(ClientLevel pLevel, SpriteSet pSprites, double pX, double pY, double pZ) {
        super(pLevel, pX, pY - 0.125D, pZ);
        this.setSize(0.01F, 0.01F);
        this.pickSprite(pSprites);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
        this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        this.hasPhysics = false;
        this.friction = 1.0F;
        this.gravity = 0.0F;
    }

    AdaptiveUnderwaterParticle(ClientLevel pLevel, SpriteSet pSprites, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY - 0.125D, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.setSize(0.01F, 0.01F);
        this.pickSprite(pSprites);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
        this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        this.hasPhysics = false;
        this.friction = 1.0F;
        this.gravity = 0.0F;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class AdaptiveUnderwaterProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public AdaptiveUnderwaterProvider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            AdaptiveUnderwaterParticle adaptiveUnderwaterParticle = new AdaptiveUnderwaterParticle(pLevel, this.sprite, pX, pY, pZ);
            int waterColor = pLevel.getBiome(new BlockPos(pX,pY,pZ)).get().getWaterColor();
            int red = (waterColor >> 16) & 0xff;
            int green = (waterColor >> 8) & 0xff;
            int blue = waterColor & 0xff;
            adaptiveUnderwaterParticle.setColor((float)red/255, (float)green/255, (float)blue/255);
            return adaptiveUnderwaterParticle;
        }
    }
}
