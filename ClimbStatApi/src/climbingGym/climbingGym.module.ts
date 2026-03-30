import { Module } from "@nestjs/common";
import { MongooseModule } from "@nestjs/mongoose";
import { Mongoose } from "mongoose";
import { ClimbingGym, ClimbingGymSchema } from "./shemas/climbingGym.shema";
import { ClimbingGymController } from "./climbingGym.controller";
import { ClimbingGymService } from "./climbingGym.service";
import { UploadModule } from "src/upload/upload.module";
import { Boulder, BoulderSchema } from "src/boulder/shemas/boulder.shema";
import { BoulderModule } from "src/boulder/boulder.module";


@Module({
    imports: [
        MongooseModule.forFeature([
            {name: ClimbingGym.name, schema: ClimbingGymSchema},
            {name: Boulder.name, schema: BoulderSchema}
        ]),
        UploadModule,
        BoulderModule
    ],
    controllers: [ClimbingGymController],
    providers: [ClimbingGymService],
    exports: [ClimbingGymService]
})
export class ClimbingGymModule {}