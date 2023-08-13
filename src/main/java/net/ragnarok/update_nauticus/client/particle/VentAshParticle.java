package net.ragnarok.update_nauticus.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class VentAshParticle extends TextureSheetParticle {

    VentAshParticle(ClientLevel pLevel, double pX, double pY, double pZ)
    {
        super(pLevel, pX, pY, pZ);
        this.gravity = 3.0E-6F;
        this.friction = 0.999F;
        this.xd *= (double)0F;
        this.zd *= (double)0F;
        this.yd = (double)0.05F;
        this.quadSize *= this.random.nextFloat() * 4.0F + 0.6F;
        this.lifetime = this.random.nextInt(360, 400);

    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getLightColor(float p_106065_) {
        return super.getLightColor(p_106065_);
    }

    @Override
    public float getQuadSize(float p_107089_)
    {
        float f = ((float)this.age + p_107089_) / (float)this.lifetime;
        return this.quadSize * (1.0F - f * f);
    }

    @Override
    public void tick() {
        //super.tick();
        //this.alpha = (-(1/(float)lifetime) * age + 1);
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ < this.lifetime && !(this.alpha <= 0.0F)) {
            this.xd += (double)(this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1));
            this.zd += (double)(this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1));
            this.yd -= (double)this.gravity;
            this.move(this.xd, this.yd, this.zd);
            if (this.age >= this.lifetime - 60 && this.alpha > 0.01F) {
                this.alpha -= 0.015F;
            }

        } else {
            this.remove();
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet sprite;

        public Provider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        public Particle createParticle(SimpleParticleType p_107103_, ClientLevel p_107104_, double p_107105_, double p_107106_, double p_107107_, double p_107108_, double p_107109_, double p_107110_)
        {
            VentAshParticle ventAshParticle = new VentAshParticle(p_107104_, p_107105_, p_107106_, p_107107_);
            ventAshParticle.pickSprite(this.sprite);
            return ventAshParticle;
        }
    }
}
