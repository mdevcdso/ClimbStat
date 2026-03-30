import { BadRequestException, Injectable } from "@nestjs/common";
import { InjectModel } from "@nestjs/mongoose";
import { Topo } from "./schema/topo.shema";
import { Model } from "mongoose";
import { TopoDto } from "./dto/topo.dto";
import { Boulder } from "src/boulder/shemas/boulder.shema";

@Injectable()
export class TopoService{
    constructor(
        @InjectModel(Topo.name) private topoModel: Model<Topo>,
        @InjectModel(Boulder.name) private boulderModel: Model<Boulder>
    ){}

    async createTopo(idBoulder: string, idUser: string, topoDto: TopoDto){
        if(idBoulder.length !== 24){
            throw new BadRequestException('Invalid boulder ID')
        }
        const existingBoulder = await this.boulderModel.findById(idBoulder).exec()
        if(!existingBoulder){
            throw new BadRequestException('Boulder not found')
        }

        const existingTopo = await this.topoModel.findOne({idBoulder, idUser}).exec()
        if(existingTopo){
            throw new BadRequestException('Topo already exists for this boulder')
        }
        const createdTopo = new this.topoModel({
            idBoulder,
            idUser,
            ...topoDto
        })
        return createdTopo.save();
    }

    async getToposByUserId(idUser: string){
        const topos = await this.topoModel.find(
            {idUser}).
            populate(
                'idBoulder',
                '_id difficulty types description image videoBeta'
            )
        .exec()

        return topos
    }

    async getToposByBoulderId(idBoulder: string){
        const topos = await this.topoModel.find(
            {idBoulder}).
            populate(
                'idUser',
                '_id username'
            )
        .exec()
        return topos
    }

    async getTopoById(id: string){
        const topo = await this.topoModel.findById(id).exec()
        if(!topo){
            throw new BadRequestException('Topo not found')
        }
        return topo
    }

    async deleteTopo(_id: string, idUser: string){
        const topo = await this.topoModel.findOne({_id, idUser}).exec()
        if(!topo){
            throw new BadRequestException('Topo not found')
        }
        return this.topoModel.findByIdAndDelete(_id).exec()
    }
}
