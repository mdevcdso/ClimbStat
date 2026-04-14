import { Prop, Schema, SchemaFactory } from "@nestjs/mongoose";

@Schema({timestamps: true})
export class ClimbingGym {
    @Prop({required: true})
    name: string

    @Prop({required: true})
    location: string

    @Prop({required: true})
    address: string

    @Prop({required: true})
    openingHours: string

    @Prop({required: true})
    clossingHours: string

    @Prop({required: true})
    description: string

    @Prop({required: false})
    franchise: string

    @Prop({required: true})
    tags: string[]

    @Prop({required: true})
    cotationType: string

    @Prop({required: true})
    image: string;

}

export const ClimbingGymSchema = SchemaFactory.createForClass(ClimbingGym)