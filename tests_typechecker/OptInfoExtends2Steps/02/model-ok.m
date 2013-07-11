Base : Alt1 Alt2 FeatureC FeatureD :: _Base ;
Alt1 : FeatureA | FeatureA2 :: _Alt1 ;
Alt2 : FeatureB | FeatureB2 :: _Alt2 ;

%%

FeatureA2 implies FeatureA ;
FeatureB2 implies FeatureB ;