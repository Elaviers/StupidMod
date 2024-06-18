package gcmod;

import gcmod.entity.PooEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PooEntityRenderer extends EntityRenderer<PooEntity>
{
    public static TexturedModelData getTexturedModelData()
    {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 17).cuboid(-6.0F, 0.0F, -6.0F, 12.0F, 3.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 6).cuboid(-4.0F, 3.0F, -4.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 1).cuboid(-2.0F, 5.0F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 32);
    }

    public static final Identifier TEXTURE = Identifier.of( "gcmod", "textures/entity/poo.png" );
    public ModelPart model;

    protected PooEntityRenderer( EntityRendererFactory.Context ctx )
    {
        super( ctx );
        this.shadowRadius = 0;
        this.model = ctx.getPart( GCModClient.POO_LAYER );
    }

    @Override
    public Identifier getTexture( PooEntity entity )
    {
        return TEXTURE;
    }

    @Override
    public void render( PooEntity poo, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light )
    {
        matrices.push();

        float size = poo.prevSize + tickDelta * (poo.size - poo.prevSize);
        if ( size > 0 )
        {
            matrices.scale( size, size, size );
            model.render( matrices, vertexConsumers.getBuffer( RenderLayer.getEntitySolid( TEXTURE ) ), light, OverlayTexture.DEFAULT_UV );
        }

        matrices.pop();

        super.render( poo, yaw, tickDelta, matrices, vertexConsumers, light );
    }
}
