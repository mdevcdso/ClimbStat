import { Controller, Delete, Get, Param, Patch, Req, UseGuards } from "@nestjs/common";
import { TopoService } from "./topo.services";
import { JwtAuthGuard } from "src/auth/guards/jwt-auth.guard";


@Controller('topo')
export class TopoController{
    constructor(
        private topoService: TopoService
    ){}

    @Get('/me')
    @UseGuards(JwtAuthGuard)
    async getToposByUserId(@Req() req){
        const userId = req.user.userId;
        return this.topoService.getToposByUserId(userId);
    }

    @Delete(':id')
    @UseGuards(JwtAuthGuard)
    async deleteTopo(@Param('id') id: string, @Req() req){
        const userId = req.user.userId;
        return this.topoService.deleteTopo(id, userId);
    }

    @Get(':id')
    @UseGuards(JwtAuthGuard)
    async getTopoById(@Param('id') id: string){
        return this.topoService.getTopoById(id);
    }

}