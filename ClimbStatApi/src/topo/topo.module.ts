import { Module } from "@nestjs/common";
import { MongooseModule } from "@nestjs/mongoose";
import { Mongoose } from "mongoose";
import { Topo, TopoSchema } from "./schema/topo.shema";
import { UploadModule } from "src/upload/upload.module";
import { BoulderController } from "src/boulder/boulder.controller";
import { BoulderService } from "src/boulder/boulder.service";
import { TopoController } from "./topo.controller";
import { TopoService } from "./topo.services";
import { Boulder, BoulderSchema } from "src/boulder/shemas/boulder.shema";

@Module({
    imports: [
        MongooseModule.forFeature([
            {name: Topo.name, schema: TopoSchema},
            {name: Boulder.name, schema: BoulderSchema}
        ]),
        UploadModule
    ],
    controllers: [TopoController],
    providers: [TopoService],
    exports: [TopoService]
})
export class TopoModule {}