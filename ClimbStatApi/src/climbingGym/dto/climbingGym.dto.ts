import { ArrayNotEmpty, IsArray, isIdentityCard, IsMongoId, isMongoId, IsNotEmpty, IsOptional, IsString } from "class-validator"

export class ClimbingGymDto {
    @IsString()
    @IsNotEmpty()
    name: string

    @IsString()
    @IsNotEmpty()
    location: string

    @IsString()
    @IsNotEmpty()
    openingHours: string

    @IsString()
    @IsOptional()
    franchise: string

    @IsString()
    @IsNotEmpty()
    cotationType: string
}