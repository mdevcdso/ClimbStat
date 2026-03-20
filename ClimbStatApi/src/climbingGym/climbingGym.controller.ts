import { Body, Controller, Get, Param, Patch, Post, UseGuards } from "@nestjs/common";
import { JwtAuthGuard } from "src/auth/guards/jwt-auth.guard";
import { ClimbingGymDto } from "./dto/climbingGym.dto";
import { ClimbingGymService } from "./climbingGym.service";
import { Roles } from "src/auth/decorators/roles.decorator";
import { UserRole } from "src/users/enums/user-role.enum";
import { UpdateClimbingGymDto } from "./dto/updateClimbingGym.dto";


@Controller('climbingGym')
export class ClimbingGymController {
    constructor(
        private climbingGymService: ClimbingGymService
    ){}

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
    @UseGuards(JwtAuthGuard)
    async createClimbingGym(@Body() climbingGymDto: ClimbingGymDto){
        return this.climbingGymService.createClimbingGym(climbingGymDto)
    }

    @Get()
    @UseGuards(JwtAuthGuard)
    async getClimbingGyms(){
        return this.climbingGymService.getClimbingGyms()
    }

}