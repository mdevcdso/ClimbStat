import { Module } from "@nestjs/common";
import { MongooseModule } from "@nestjs/mongoose";
import { Mongoose } from "mongoose";
import { ClimbingGym, ClimbingGymSchema } from "./shemas/climbingGym.shema";
import { ClimbingGymController } from "./climbingGym.controller";
import { ClimbingGymService } from "./climbingGym.service";


@Module({
    imports: [
        MongooseModule.forFeature([
            {name: ClimbingGym.name, schema: ClimbingGymSchema}
        ])
    ],
    controllers: [ClimbingGymController],
    providers: [ClimbingGymService],
    exports: [ClimbingGymService]
})
export class ClimbingGymModule {}