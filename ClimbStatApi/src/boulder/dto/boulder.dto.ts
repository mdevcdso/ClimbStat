import { IsEnum, isEnum, IsMongoId, IsNotEmpty, IsString } from "class-validator";
import { isTypedArray } from "util/types";
import { BoulderType } from "../enums/boulder-type.enum";


export class BoulderDto {
    
    @IsNotEmpty()
    @IsString()
    difficulty: string;

    @IsNotEmpty()
    @IsEnum(BoulderType, { each: true })
    types: string[];

    @IsNotEmpty()
    @IsString()
    description: string;
}