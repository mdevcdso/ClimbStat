import { Prop, Schema, SchemaFactory } from "@nestjs/mongoose";
import { Mongoose } from "mongoose";
import { Schema as MongooseSchema } from "mongoose";

@Schema({timestamps: true})
export class Topo{
    @Prop({
        required: true,
        type: MongooseSchema.Types.ObjectId,
        ref: 'Boulder'
    })
    idBoulder: string;

    @Prop({
        required: true,
        type: MongooseSchema.Types.ObjectId,
        ref: 'User'
    })
    idUser: string;

    @Prop({required: true})
    isFlash: boolean;

    @Prop({required: true})
    nbAttempts: number;
}

export const TopoSchema = SchemaFactory.createForClass(Topo)