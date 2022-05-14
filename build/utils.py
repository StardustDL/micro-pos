from pathlib import Path
import os
from platform import system
import shutil
from coxbuild.schema import task, group, named, run as innerRun, depend, ext, withExecutionState, ExecutionState, withProject, ProjectSettings, withConfig, Configuration


root = Path(".")


def projects():
    for subdir in root.iterdir():
        if subdir.is_dir() and (subdir / "build.gradle").exists():
            yield subdir.name


def prun(*args, **kwargs):
    innerRun(*args, **kwargs, shell=system() == "Windows")


def ensureDir(dir: Path):
    if dir.exists() and dir.is_dir():
        return
    os.makedirs(dir)
