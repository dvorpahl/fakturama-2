#!/bin/zsh

emulate -LR zsh # reset zsh options

# DEVELOPER_PASSWORD is set in .bash_profile

# by Andy Maloney
# http://asmaloney.com/2013/07/howto/packaging-a-mac-os-x-application-using-a-dmg/

die() {
	local m="$1"  # the first arg 
	local e=$2    # the second arg
	echo "$m" 
	exit $e
}

TARGET_PRODUCT_ROOT=../../target/products

# make sure we are in the correct dir when we double-click a .command file
dir=${0%/*}
if [ -d "$dir" ]; then
  cd "$dir"
fi

# set up your app name, version number, and background image file name
VERSION=2.2.0-RC
INSTALL_DIR=../install
INSTALLER_NAME=Installer_Fakturama
   
# if not enough args displayed, display an error and die
[ $# -eq 0 ] && die "Notarizing Fakturama MacOS installer files. Usage: $0 1|2 
where 1 ... amd64
      2 ... aarch64" 1

for arg in "$@"; do
   
   if [ $arg -eq "1" ]; then
     ARCHITECTURE="amd64"
   elif [ $arg -eq "2" ]; then
     ARCHITECTURE="aarch64"
   else 
     die "Usage: $0 1|2 
     where 1 ... amd64
           2 ... aarch64" 1
   fi

   echo "*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*"
   echo "*= creating installer for $ARCHITECTURE with version $VERSION                                   =*"
   echo "*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*"
   
   # you should not need to change this
   VOL_NAME="${INSTALLER_NAME}_macos-${ARCHITECTURE}_${VERSION}"   # volume name will be "Installer_Fakturama_macos-amd64_2.1.0”
   DMG_FINAL="${INSTALL_DIR}/${VOL_NAME}.dmg"                      # final DMG name will be "Installer_Fakturama_macos-amd64_2.1.1.dmg"
      
   echo 'notarize application...'
   xcrun notarytool submit ${DMG_FINAL} --keychain-profile "Fakturama-Build" --wait
   if [ $? -eq 0 ]
   then
      echo "Successfully notarized ${DMG_FINAL}"
	  xcrun stapler staple ${DMG_FINAL}
	  spctl --assess --type open --context context:primary-signature --verbose "${DMG_FINAL}"
   else
   	  echo "*!*!*!*!*!*!*!*!*!*! Could not notarize ${DMG_FINAL}" >&2
   fi
   
   # manual check  		
   # xcrun notarytool log e0eab77e-76aa-40e2-af05-113abb21a890 --keychain-profile "Fakturama-Build"   		
   
   # some additional work for Linux and Windows archives
   for arch in x86_64 aarch64; do
      if [ -f ${TARGET_PRODUCT_ROOT}/Fakturama.ID-linux.gtk.$arch.tar.gz ]; then
      	 echo "moving Linux installer (tar.gz) to installer directory (${INSTALL_DIR}) for ${arch}"
      	 mv ${TARGET_PRODUCT_ROOT}/Fakturama.ID-linux.gtk.${arch}.tar.gz ${INSTALL_DIR}/${INSTALLER_NAME}_linux-${arch}_${VERSION}.tar.gz
      fi
   done
done
   
echo 'Done.'
# exit 0
