import { BoulderService } from "./boulder.service";
import { Controller, Post, Patch, Param, Body, UseGuards, UseInterceptors, UploadedFile, Get, BadRequestException } from "@nestjs/common";
import { FileInterceptor } from "@nestjs/platform-express";
import { BoulderDto } from "./dto/boulder.dto";
import { JwtAuthGuard } from "src/auth/guards/jwt-auth.guard";
import { Roles } from "src/auth/decorators/roles.decorator";
import { UserRole } from "src/users/enums/user-role.enum";


@Controller('boulder')
export class BoulderController{
    constructor(
        private boulderService: BoulderService
    ){}
    
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