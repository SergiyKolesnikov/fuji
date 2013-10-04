ZipMe : Base Compression [Extract] [ArchiveCheck] Checksum [Derivatives] :: _ZipMe ;

Compression : Compress [GZIP] :: _Compression ;

Checksum : [CRC] [Adler32Checksum] :: _Checksum ;

Derivatives : [DerivativeCompressAdler32Checksum] [DerivativeCompressCRC] [DerivativeCompressGZIP] [DerivativeCompressGZIPCRC] [DerivativeExtractCRC] [DerivativeGZIPCRC] :: _Derivatives ;

%%

GZIP implies CRC ;
Compress and Adler32Checksum iff DerivativeCompressAdler32Checksum ;
Compress and CRC iff DerivativeCompressCRC ;
Compress and GZIP iff DerivativeCompressGZIP ;
Compress and GZIP and CRC iff DerivativeCompressGZIPCRC ;
Extract and CRC iff DerivativeExtractCRC ;
GZIP and CRC iff DerivativeGZIPCRC ;

