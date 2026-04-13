import { BadRequestException, Body, Controller, Get, Param, Patch, Post, UploadedFile, UseGuards, UseInterceptors } from "@nestjs/common";
import { JwtAuthGuard } from "src/auth/guards/jwt-auth.guard";
import { ClimbingGymDto } from "./dto/climbingGym.dto";
import { ClimbingGymService } from "./climbingGym.service";
import { Roles } from "src/auth/decorators/roles.decorator";
import { UserRole } from "src/users/enums/user-role.enum";
import { UpdateClimbingGymDto } from "./dto/updateClimbingGym.dto";
import { FileInterceptor } from "@nestjs/platform-express";
import { BoulderDto } from "src/boulder/dto/boulder.dto";
import { BoulderService } from "src/boulder/boulder.service";
import { ApiBody } from "@nestjs/swagger";


@Controller('climbingGym')
export class ClimbingGymController {
    constructor(
        private climbingGymService: ClimbingGymService,
        private boulderService: BoulderService
    ){}

    @Get(":gym_id/boulder")
    @UseGuards(JwtAuthGuard)
    async getBoulders(@Param('gym_id') gymId: string){
        return this.boulderService.getBouldersByGymId(gymId)
    }

    @Roles(UserRole.ADMIN)
    @Post(':gym_id/boulder')
    @ApiBody({ type: BoulderDto })
    @UseGuards(JwtAuthGuard)
    @UseInterceptors(FileInterceptor('image'))
    async createBoulder(
        @Body() boulderDto: BoulderDto,
        @UploadedFile() file: Express.Multer.File,
        @Param('gym_id') gymId: string
    ) {
        if(!file) throw new BadRequestException('Image file is required');
        return this.boulderService.createBoulder(boulderDto, file, gymId);
    }

    @Get(':id')
    @UseGuards(JwtAuthGuard)
    async getClimbingGymById(@Param('id') id: string){
        return this.climbingGymService.getClimbingGymById(id)
    }

    @Roles(UserRole.ADMIN)
    @Patch(':id')
    @UseGuards(JwtAuthGuard)
    async updateClimbingGym(@Param('id') id: string, @Body() updateClimbingGymDto: UpdateClimbingGymDto){
        return this.climbingGymService.updateClimbingGym(id, updateClimbingGymDto)
    }

    @Roles(UserRole.ADMIN)
    @Post()
    @ApiBody({ type: ClimbingGymDto })
    @UseGuards(JwtAuthGuard)
    @UseInterceptors(FileInterceptor('image'))
    async createClimbingGym(
        @Body() climbingGymDto: ClimbingGymDto,
        @UploadedFile() file: Express.Multer.File
    ){
        if(!file) throw new BadRequestException('Image file is required');
        return this.climbingGymService.createClimbingGym(climbingGymDto, file);
    }

    @Get()
    @UseGuards(JwtAuthGuard)
    async getClimbingGyms(){
        return this.climbingGymService.getClimbingGyms()
    }

}