import { Injectable, NotFoundException } from "@nestjs/common";
import { InjectModel } from "@nestjs/mongoose";
import { Boulder } from "./shemas/boulder.shema";
import { Model } from "mongoose";
import { UpdateBoulderDto } from "./dto/updateBoulder.dto";
import { NotFoundError } from "rxjs";
import * as fs from 'fs';
import { BoulderDto } from "./dto/boulder.dto";


@Injectable()
export class BoulderService{
    constructor(
        @InjectModel(Boulder.name) private boulderModel: Model<Boulder>
    ){}

    async createBoulder(boulderDto: BoulderDto, file: Express.Multer.File, gymId: string){
        const imageUrl = `${process.env.APP_URL}/uploads/${file.filename}`;

        const createdBoulder = new this.boulderModel({
            ...boulderDto,
            idGym: gymId,
            image: imageUrl,
        });
        return createdBoulder.save();
    }

    async getBouldersByGymId(gymId: string){
        if(gymId.length !== 24){
            throw new NotFoundException('Invalid climbing gym ID')
        }
        return this.boulderModel
            .find( {idGym: gymId})
            .populate('idGym', 'id name location franchise')
        .exec()
    }

    async getBoulders(){
        return this.boulderModel.find().exec()
    }

    async getBoulderById(id: string){
        return this.boulderModel.findOne({_id: id}).exec()
    }

    async updateBoulder(id: string, updateBoulderDto: UpdateBoulderDto, file?: Express.Multer.File){
        const boulder = await this.boulderModel.findById(id).exec()
        if(!boulder){
            throw new NotFoundException('Boulder not found')
        }
        var image = ""
        if(file){
            const boulder = await this.boulderModel.findById(id);
            if (boulder?.image) {
                const oldPath = `./uploads/${boulder.image.split('/uploads/')[1]}`;
                if (fs.existsSync(oldPath)) fs.unlinkSync(oldPath);
            }
            image = `${process.env.APP_URL}/uploads/${file.filename}`;
        }
        return this.boulderModel.findByIdAndUpdate(
            id,
            {
                ...updateBoulderDto,
                image: image || boulder.image
            },
            {new: true}
        ).exec()
    }
}