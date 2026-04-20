import { Prop, Schema, SchemaFactory } from "@nestjs/mongoose";
import { Schema as MongooseSchema } from "mongoose";

@Schema({ timestamps: true })
export class Topo {
    @Prop({
        required: true,
        type: MongooseSchema.Types.ObjectId,
        ref: 'Boulder'
    })
    idBoulder!: string;

    @Prop({
        required: true,
        type: MongooseSchema.Types.ObjectId,
        ref: 'User'
    })
    idUser!: string;

    @Prop({ required: true })
    isFlash!: boolean;

    @Prop({ required: true })
    nbAttempts!: number;

    @Prop({ required: false })
    comment?: string;
}

export const TopoSchema = SchemaFactory.createForClass(Topo);
