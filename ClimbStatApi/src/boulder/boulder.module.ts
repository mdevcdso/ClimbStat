import { Module } from "@nestjs/common";
import { MongooseModule } from "@nestjs/mongoose";
import { Boulder, BoulderSchema } from "./shemas/boulder.shema";
import { BoulderController } from "./boulder.controller";
import { BoulderService } from "./boulder.service";
import { UploadModule } from "src/upload/upload.module";


@Module({
    imports: [
        MongooseModule.forFeature([
            {name: Boulder.name, schema: BoulderSchema}
        ]),
        UploadModule
    ],
    controllers: [BoulderController],
    providers: [BoulderService],
    exports: [BoulderService]
})
export class BoulderModule {}