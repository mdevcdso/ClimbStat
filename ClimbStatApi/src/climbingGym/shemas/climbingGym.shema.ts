import { Prop, Schema, SchemaFactory } from "@nestjs/mongoose";

@Schema({timestamps: true})
export class ClimbingGym {
    @Prop({required: true})
    name: string

    @Prop({required: true})
    location: string

    @Prop({required: true})
    openingHours: string

    @Prop({required: false})
    franchise: string

    @Prop({required: true})
    cotationType: string
}

export const ClimbingGymSchema = SchemaFactory.createForClass(ClimbingGym)