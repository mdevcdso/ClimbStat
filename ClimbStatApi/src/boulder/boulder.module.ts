import { Module } from "@nestjs/common";
import { MongooseModule } from "@nestjs/mongoose";
import { Boulder, BoulderSchema } from "./shemas/boulder.shema";
import { BoulderController } from "./boulder.controller";
import { BoulderService } from "./boulder.service";
import { UploadModule } from "src/upload/upload.module";
import { Topo, TopoSchema } from "src/topo/schema/topo.shema";
import { TopoModule } from "src/topo/topo.module";
import { ClimbingGym, ClimbingGymSchema } from "src/climbingGym/shemas/climbingGym.shema";
import { ClimbingGymModule } from "src/climbingGym/climbingGym.module";


@Module({
    imports: [
        MongooseModule.forFeature([
            {name: Boulder.name, schema: BoulderSchema},
            {name: ClimbingGym.name, schema: ClimbingGymSchema},
        ]),
        UploadModule,
        TopoModule,
    ],
    controllers: [BoulderController],
    providers: [BoulderService],
    exports: [BoulderService]
})
export class BoulderModule {}