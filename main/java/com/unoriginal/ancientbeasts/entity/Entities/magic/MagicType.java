package com.unoriginal.ancientbeasts.entity.Entities.magic;

public enum MagicType {

        NONE(0, 0.0D, 0.0D, 0.0D),
        SUMMON(3, 0.7D, 0.5D, 0.2D);


        private final int id;
        private final double[] particleSpeed;

        MagicType(int idIn, double xParticleSpeed, double yParticleSpeed, double zParticleSpeed) {
            this.id = idIn;
            this.particleSpeed = new double[]{xParticleSpeed, yParticleSpeed, zParticleSpeed};
        }

        public double[] particleSpeed(){
            return this.particleSpeed;
        }

        public int getId() {
            return id;
        }

        public static MagicType getFromId(int idIn) {
            for(MagicType magicType : values()) {
                if (idIn == magicType.id) {
                    return magicType;
                }
            }

            return NONE;
        }
}
