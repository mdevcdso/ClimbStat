import { BoulderService } from "./boulder.service";
import { Controller, Post, Patch, Param, Body, UseGuards, UseInterceptors, UploadedFile, Get, BadRequestException, Req } from "@nestjs/common";
import { FileInterceptor } from "@nestjs/platform-express";
import { BoulderDto } from "./dto/boulder.dto";
import { JwtAuthGuard } from "src/auth/guards/jwt-auth.guard";
import { Roles } from "src/auth/decorators/roles.decorator";
import { UserRole } from "src/users/enums/user-role.enum";
import { TopoService } from "src/topo/topo.services";
import { TopoDto } from "src/topo/dto/topo.dto";


@Controller('boulder')
export class BoulderController{
    constructor(
        private boulderService: BoulderService,
        private topoService: TopoService
    ){}

    @Post(':boulderId/topo')
    @UseGuards(JwtAuthGuard)
    async createTopo( 
        @Param('boulderId') boulderId: string, 
        @Body() topoDto: TopoDto, 
        @Req() req
    ) {
        const userId = req.user.userId;
        return this.topoService.createTopo(boulderId, userId, topoDto);
    }

    @Get(':boulderId/topo')
    @UseGuards(JwtAuthGuard)
    async getToposByBoulderId(@Param('boulderId') boulderId: string) {
        return this.topoService.getToposByBoulderId(boulderId);
    }
    
    @Roles(UserRole.ADMIN)
    @Patch(':id')
    @UseGuards(JwtAuthGuard)
    @UseInterceptors(FileInterceptor('image'))
    async updateBoulder(
        @Param('id') id: string,
        @Body() updateBoulderDto: any,
        @UploadedFile() file: Express.Multer.File
    ) {
        return this.boulderService.updateBoulder(id, updateBoulderDto, file);
    }

}