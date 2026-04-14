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
    address: string

    @IsString()
    @IsNotEmpty()
    openingHours: string

    @IsString()
    @IsNotEmpty()
    clossingHours: string

    @IsString()
    @IsNotEmpty()
    description: string

    @IsString()
    @IsOptional()
    franchise: string

    @IsArray()
    @ArrayNotEmpty()
    @IsString({each: true})
    tags: string[]

    @IsString()
    @IsNotEmpty()
    cotationType: string
}