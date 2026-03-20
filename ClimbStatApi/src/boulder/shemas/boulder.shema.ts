import { Prop, Schema, SchemaFactory } from "@nestjs/mongoose";
import { Schema as MongooseSchema } from "mongoose";
import { BoulderType } from "../enums/boulder-type.enum";


@Schema()
export class Boulder{
    
    @Prop({required: true})
    difficulty: string;

    @Prop({required: true})
    types: [BoulderType];

    @Prop({required: true})
    description: string;

    @Prop({required: true})
    image: string;

    @Prop({required: false})
    videoBeta: string;

    @Prop({
        required: true,
        type: MongooseSchema.Types.ObjectId,
        ref: 'ClimbingGym'
    })
    idGym: string;
}

export const BoulderSchema = SchemaFactory.createForClass(Boulder)