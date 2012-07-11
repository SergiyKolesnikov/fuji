Base : FeatureA [FeatureB] [FeatureC] FeatureD [FeatureB2] [FeatureB3] :: _Base ;

%%

FeatureB iff ((not FeatureB2) and (not FeatureB3)) ;
FeatureB2 iff ((not FeatureB) and (not FeatureB3)) ;
FeatureB implies FeatureC ;
FeatureB2 implies FeatureC ;
FeatureB3 implies FeatureC ;











