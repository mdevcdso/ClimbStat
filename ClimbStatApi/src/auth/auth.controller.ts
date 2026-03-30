import { Controller, Get, UseGuards } from "@nestjs/common";
import { AuthService } from "./auth.service";
import { CreateUserDto } from "src/users/dto/create-user.dto";
import { Body, Post } from "@nestjs/common";
import { JwtAuthGuard } from "./guards/jwt-auth.guard";
import { GetUser } from "./decorators/get-user.decorator";
import { LoginDto } from "./dto/login.dto";
import { ApiBody, ApiResponse } from "@nestjs/swagger";
import { UserSchema } from "src/users/schemas/user.schema";



@Controller('auth')
export class AuthController {
    constructor(private authService: AuthService) {}

    @Post()
    @ApiBody({ type: CreateUserDto })
    async register(@Body() CreateUserDto: CreateUserDto) {
        return this.authService.register(CreateUserDto);
    }

    @Post('login')
    @ApiBody({ type: LoginDto })
    async login(@Body() loginUserDto: LoginDto) {
        return this.authService.login(loginUserDto);
    }

}