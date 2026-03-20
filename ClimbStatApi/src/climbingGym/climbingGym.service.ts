import { BadRequestException, Injectable, NotFoundException } from "@nestjs/common";
import { InjectModel } from "@nestjs/mongoose";
import { ClimbingGym } from "./shemas/climbingGym.shema";
import { Model } from "mongoose";
import { ClimbingGymDto } from "./dto/climbingGym.dto";
import { UpdateClimbingGymDto } from "./dto/updateClimbingGym.dto";
import { NotFoundError } from "rxjs";


@Injectable()
export class ClimbingGymService {
    constructor(
        @InjectModel(ClimbingGym.name) private climbingGymModel: Model<ClimbingGym>
    ){}

    async createClimbingGym(ClimbingGymDto: ClimbingGymDto){
        const existingClimbingGym = await this.climbingGymModel.findOne({name: ClimbingGymDto.name}).exec()
        if(existingClimbingGym){
            throw new BadRequestException('A climbing gym with this name already exists')
        }
        const createdClimbingGym = new this.climbingGymModel(ClimbingGymDto)
        return createdClimbingGym.save()
    }

    async getClimbingGyms(){
        return this.climbingGymModel.find().exec()
    }

    async getClimbingGymById(id: string){
        return this.climbingGymModel.findOne({_id: id}).exec()
    }

    async updateClimbingGym(id: string, updateClimbingGymDto: UpdateClimbingGymDto){
        const existingClimbingGym = await this.climbingGymModel.findOne({_id: id}).exec()
        if(!existingClimbingGym){
            throw new NotFoundException('Climbing gym not found')
        }
        return this.climbingGymModel.findByIdAndUpdate(
            id, 
            {...updateClimbingGymDto }, 
            {new: true}
        ).exec()
    }
    
}